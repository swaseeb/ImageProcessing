import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.client.program.Client;
import org.apache.flink.client.program.ContextEnvironment;
import org.apache.flink.streaming.api.JobGraphBuilder;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamContextEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.util.ClusterUtil;

import javax.imageio.ImageIO;

public class ImageStreamExecutionEnvironment extends StreamExecutionEnvironment {

	private static ImageStreamExecutionEnvironment contextEnvironment;
	private static int defaultLocalDop = Runtime.getRuntime().availableProcessors();

	protected ImageStreamExecutionEnvironment() {super();
	}
	public static ImageStreamExecutionEnvironment getExecutionEnvironment() {
		return new ImageStreamExecutionEnvironment();
	}


	/**
	 * Creates a {@link LocalStreamEnvironment}. The local execution environment
	 * will run the program in a multi-threaded fashion in the same JVM as the
	 * environment was created in. The default degree of parallelism of the
	 * local environment is the number of hardware contexts (CPU cores /
	 * threads), unless it was specified differently by
	 * {@link #setDegreeOfParallelism(int)}.
	 *
	 * @return A local execution environment.
	 */
	public static LocalStreamEnvironment createLocalEnvironment() {
		return createLocalEnvironment(defaultLocalDop);
	}

	public DataStreamSource<BufferedImage> readImageFile(String filePath) {
		checkIfFileExists(filePath);
		return addSource(new ImageSourceFunction(filePath));
	}
	public void saveImageFile(BufferedImage img) throws IOException {

		ImageIO.write(img, "png", new File("Cate.png"));

	}
	private static void checkIfFileExists(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found"  + filePath);
		}

		if (!file.canRead()) {
			throw new IllegalArgumentException("Cannot read file"  + filePath);
		}

		if (file.isDirectory()) {
			throw new IllegalArgumentException("Given path is a directory"  + filePath);
		}
	}

	/**
	 * Creates a {@link LocalStreamEnvironment}. The local execution environment
	 * will run the program in a multi-threaded fashion in the same JVM as the
	 * environment was created in. It will use the degree of parallelism
	 * specified in the parameter.
	 *
	 * @param degreeOfParallelism
	 *            The degree of parallelism for the local environment.
	 * @return A local execution environment with the specified degree of
	 *         parallelism.
	 */
	public static LocalStreamEnvironment createLocalEnvironment(int degreeOfParallelism) {
		LocalStreamEnvironment lee = new LocalStreamEnvironment();
		lee.setDegreeOfParallelism(degreeOfParallelism);
		return lee;
	}


	@Override
	public void execute() throws Exception {
		 //TODO Auto-generated method stub

	}

	@Override
	public void execute(String jobName) throws Exception {
		//TODO Auto-generated method stub
		ClusterUtil.runOnMiniCluster(this.jobGraphBuilder.getJobGraph(jobName),
				getDegreeOfParallelism());
	}

}
