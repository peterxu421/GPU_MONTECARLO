package examples;

import com.nativelibs4java.opencl.*;

/**
 * Created with IntelliJ IDEA.
 * User: eran
 * Date: 8/24/13
 * Time: 2:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Example4 {

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

    }

}
