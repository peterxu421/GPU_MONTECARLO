package examples;

import com.nativelibs4java.opencl.CLDevice;
import com.nativelibs4java.opencl.CLPlatform;
import com.nativelibs4java.opencl.JavaCL;

/**
 * In this example we are going to print the platform details for the computer and all th devices in each platform
 */
public class Example2 {

    public static void main(String[] args){
        // Creating the array of platform in our computer
        CLPlatform[] clPlatforms = JavaCL.listPlatforms();
        // Iterating over the platform and reporing some details about them
        for (CLPlatform platform : clPlatforms ){
            System.out.println("*** New Platform ***" );
            System.out.println("Vendor: " + platform.getVendor() );
            System.out.println("Name: " + platform.getName() );
            System.out.println("Extensions: " + String.format("%s\t", platform.getExtensions()));
            // getting the devices in the platform
            CLDevice[] clDevices = platform.listAllDevices(false);
            System.out.println(clDevices.length);
            // iterate over the devices and print their capabilities
            for (CLDevice device : clDevices ){
                // printing the device capabilities
                System.out.println("*** New device *** ");
                System.out.println("Vendor: " + device.getVendor());
                System.out.println("Name: " + device.getName() );
                System.out.println("Type: " + device.getType() );
                System.out.println("Global Memory size: " + (device.getGlobalMemSize()/1024000) + "M");
                System.out.println("Local Mem size: " + device.getLocalMemSize());
                System.out.println("Max work items: " + device.getMaxWorkItemSizes()[0] + "x"
                        + device.getMaxWorkItemSizes()[1] + "x" + device.getMaxWorkItemSizes()[2]);
                System.out.println("Number of Computing Units: " + device.getMaxComputeUnits() );
                System.out.println( "Execution Capabilities: " + device.getExecutionCapabilities());
            }

        }
    }





}
