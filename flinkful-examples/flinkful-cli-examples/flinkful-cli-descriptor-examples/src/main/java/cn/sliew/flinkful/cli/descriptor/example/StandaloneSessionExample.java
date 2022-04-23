package cn.sliew.flinkful.cli.descriptor.example;

import cn.sliew.flinkful.cli.base.CliClient;
import cn.sliew.flinkful.cli.base.PackageJarJob;
import cn.sliew.flinkful.cli.descriptor.DescriptorCliClient;
import cn.sliew.flinkful.common.enums.DeploymentTarget;
import cn.sliew.flinkful.common.examples.FlinkExamples;
import org.apache.flink.configuration.*;
import org.apache.flink.runtime.jobgraph.SavepointRestoreSettings;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

public class StandaloneSessionExample {

    public static void main(String[] args) throws Exception {
        CliClient client = new DescriptorCliClient();
        client.submit(DeploymentTarget.STANDALONE_SESSION, buildConfiguration(), buildJarJob());
    }

    /**
     * 通过 {@link PipelineOptions#JARS} 将任务 jar 包和对应的依赖都可以一起传到 JobManager。
     * 通过这种方式，可以避免在 JobManager 手动添加 seatunnel-core-flink.jar 或 mysql-connector-java.jar
     */
    private static Configuration buildConfiguration() throws MalformedURLException {
        Configuration configuration = FlinkExamples.loadConfiguration();
        configuration.setString(JobManagerOptions.ADDRESS, "localhost");
        configuration.setInteger(JobManagerOptions.PORT, 6123);
        configuration.setInteger(RestOptions.PORT, 8081);
        URL exampleUrl = new File(FlinkExamples.EXAMPLE_JAR).toURL();
        ConfigUtils.encodeCollectionToConfig(configuration, PipelineOptions.JARS, Collections.singletonList(exampleUrl), Object::toString);
        return configuration;
    }

    private static PackageJarJob buildJarJob() {
        PackageJarJob job = new PackageJarJob();
        job.setJarFilePath(FlinkExamples.EXAMPLE_JAR);
        job.setEntryPointClass(FlinkExamples.EXAMPLE_ENTRY_CLASS);
        job.setProgramArgs(new String[]{});
        job.setClasspaths(Collections.emptyList());
        job.setSavepointSettings(SavepointRestoreSettings.none());
        return job;
    }
}
