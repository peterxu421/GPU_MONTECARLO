package examples;

import com.nativelibs4java.opencl.*;

/**
 * Created with IntelliJ IDEA.
 * User: eran
 * Date: 8/24/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Example5 {

    public static void main(String[] args){

        // Creating the platform which is out computer.
        CLPlatform clPlatform = JavaCL.listPlatforms()[0];
        // Getting the GPU device
        //CLDevice device = clPlatform.listGPUDevices(true)[0];
        CLDevice device = clPlatform.listAllDevices(true)[0];
        // Verifing that we have the GPU device
        System.out.println("*** New device *** ");
        System.out.println("Vendor: " + device.getVendor());
        System.out.println("Name: " + device.getName() );
        System.out.println("Type: " + device.getType() );
        // Let's make a context
        CLContext context = JavaCL.createContext(null, device);
        // Lets make a default FIFO queue.
        CLQueue queue = context.createDefaultQueue();

        // Read the program sources and compile them :
        String src = "__kernel void add_floats(__global const float* a, __global const float* b, __global float* out, int n) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    if (i >= n)\n" +
                "        return;\n" +
                "\n" +
                "    out[i] = a[i] + b[i];\n" +
                "}\n" +
                "\n" +
                "__kernel void fill_in_values(__global float* a, __global float* b, int n) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    if (i >= n)\n" +
                "        return;\n" +
                "\n" +
                "    a[i] = cos((float)i);\n" +
                "    b[i] = sin((float)i);\n" +
                "}";
        CLProgram program = context.createProgram(src);
        program.build();

        CLKernel kernel = program.createKernel("add_floats");


    }

}
