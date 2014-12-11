Xu Jingwei N10526523

1. Open the IntelliJ;
2. Clone all files from https://github.com/peterxu421/GPU_MONTECARLO.git to a local directory;
3. Create a new Maven project based on the copied files;
4. Open the “SimulationManager.java", and run it. The result is shown in the console.
5. Open all the test files and run it.

RESULTS_GPU: shows the result for Question1 and Question2 with number of simulations

Explanation of the method:
I created UniformRandomGenerator which generates the uniform random number implements the I_RandomVectorGenerator interface. Also I created GPUNormalRandomGenerator which uses UniformRandomGenerator, takes uniform random vector to the best device that kernel finds (typically GPU), applies the Box-Muller transformation on it and exact the value to form a normal random vector.
