package monteCarlo;

import com.nativelibs4java.opencl.*;
import org.bridj.Pointer;

import java.util.Random;

import static org.bridj.Pointer.allocateFloats;

/**
 * Created by jingweixu on 11/12/14.
 */
public class GPUNormalRandomNumberGenerator implements I_RandomVectorGenerator {
    // length of random vector required
    private int N;
    // batch number required
    private int batchNum;
    // store the normal random vector stored in GPU to generate Gaussian
    private double[] normalSequence;
    // tracks whether we need to regenerate the normal random variable or not
    private int idx;

    /*
    * The constructor of GPU Generator
    * */
    public GPUNormalRandomNumberGenerator(int N, int batchNum){
        this.N = N;
        this.batchNum = batchNum;
        normalSequence = getGPUGaussian(this.batchNum);
        idx = 0;
    }

    @Override
    /*
    * This function aims to generate the normal random variable with length N
    * Note that in here we use the GPU to compute 2 million numbers
    * */
    public double[] getVector() {
        double[] vector = new double[N];
        for(int i = 0; i < N; i++){
            // if the idx reaches the generated amount of normal random number
            // we regenerate a batch of random number again
            if(idx == 2*batchNum - 1) {
                normalSequence = getGPUGaussian(this.batchNum);
            }
            // we assign the generated normal to the vector needed for the simulation
            vector[i] = normalSequence[idx];
            idx++;
        }
        return vector;
    }

    /*
    * This function aims to generate normal random variable using the best device chosen by kernal
    * Note that it will return an array with size batchNum
    * */
    public double[] getGPUGaussian(int batchNum){

        idx = 0;
        // Creating the platform which is out computer.
        CLPlatform clPlatform = JavaCL.listPlatforms()[0];

        // Getting the all devices
        CLDevice device = clPlatform.listAllDevices(true)[0];

        // Let's make a context
        CLContext context = JavaCL.createContext(null, device);

        // Lets make a default FIFO queue.
        CLQueue queue = context.createDefaultQueue();

        // Read the program sources and compile them :
        String src = "__kernel void fill_in_values(__global const float* a, __global const float* b, __global float* out1, __global float* out2, float pi, int n) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    if (i >= n)\n" +
                "        return;\n" +
                "\n" +
                "    out1[i] = sqrt(-2*log(a[i]))*cos(2*pi*b[i]);\n" +
                "    out2[i] = sqrt(-2*log(a[i]))*sin(2*pi*b[i]);\n" +
                "}";
        CLProgram program = context.createProgram(src);
        program.build();

        CLKernel kernel = program.createKernel("fill_in_values");

        final int n = batchNum;
        final Pointer<Float>
                aPtr = allocateFloats(n),
                bPtr = allocateFloats(n);
        // store the uniform vector in order for GPU to generate Gaussian
        double[] uniformSequence = (new UniformRandomNumberGenerator(2*batchNum)).getVector();
        // Generate uniform sequence and assign to aPtr and bPtr
        for (int i = 0; i < n; i++) {
            aPtr.set(i, (float) uniformSequence[2*i]);
            bPtr.set(i, (float) uniformSequence[2*i+1]);
            //System.out.println(uniformSequence[2*i]);
        }

        // Create OpenCL input buffers (using the native memory pointers aPtr and bPtr) :
        CLBuffer<Float>
                a = context.createFloatBuffer(CLMem.Usage.Input, aPtr),
                b = context.createFloatBuffer(CLMem.Usage.Input, bPtr);

        // Create an OpenCL output buffer :
        CLBuffer<Float>
                out1 = context.createFloatBuffer(CLMem.Usage.Output, n),
                out2 = context.createFloatBuffer(CLMem.Usage.Output, n);

        kernel.setArgs(a, b, out1, out2, (float) Math.PI, batchNum);
        CLEvent event = kernel.enqueueNDRange(queue, new int[]{n}, new int[]{128});
        event.invokeUponCompletion(new Runnable() {
            @Override
            public void run() {

            }
        });
        final Pointer<Float> c1Ptr = out1.read(queue,event);
        final Pointer<Float> c2Ptr = out2.read(queue,event);

        double[] normalSequence = new double[2*batchNum];
        for(int i = 0; i < batchNum; i++){
            normalSequence[2*i] = (double) c1Ptr.get(i);
            normalSequence[2*i+1] = (double) c2Ptr.get(i);
        }
        return normalSequence;
    }

}
