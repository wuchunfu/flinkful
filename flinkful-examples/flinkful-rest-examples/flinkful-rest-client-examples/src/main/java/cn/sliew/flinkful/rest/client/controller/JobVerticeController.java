package cn.sliew.flinkful.rest.client.controller;

import cn.sliew.flinkful.rest.base.RestClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.flink.runtime.rest.messages.JobVertexAccumulatorsInfo;
import org.apache.flink.runtime.rest.messages.JobVertexBackPressureInfo;
import org.apache.flink.runtime.rest.messages.JobVertexDetailsInfo;
import org.apache.flink.runtime.rest.messages.job.SubtaskExecutionAttemptDetailsInfo;
import org.apache.flink.runtime.rest.messages.job.SubtasksAllAccumulatorsInfo;
import org.apache.flink.runtime.rest.messages.job.metrics.MetricCollectionResponseBody;
import org.apache.flink.runtime.webmonitor.threadinfo.JobVertexFlameGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/flink/job-vertices")
@Api(value = "/job-vertices", tags = "job vertice 接口")
public class JobVerticeController {

    @Autowired
    private RestClient client;

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid
     */
    @GetMapping("{jobId}/vertices/{vertexId}")
    @ApiOperation("Returns details for a task, with a summary for each of its subtasks.")
    public CompletableFuture<JobVertexDetailsInfo> jobVertexDetail(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId) throws IOException {

        return client.jobVertice().jobVertexDetail(jobId, vertexId);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-accumulators
     */
    @GetMapping("{jobId}/vertices/{vertexId}/accumulators")
    @ApiOperation("Returns user-defined accumulators of a task, aggregated across all subtasks.")
    public CompletableFuture<JobVertexAccumulatorsInfo> jobVertexAccumulators(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId) throws IOException {

        return client.jobVertice().jobVertexAccumulators(jobId, vertexId);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-backpressure
     */
    @GetMapping("{jobId}/vertices/{vertexId}/backpressure")
    @ApiOperation("Returns back-pressure information for a job, and may initiate back-pressure sampling if necessary.")
    public CompletableFuture<JobVertexBackPressureInfo> jobVertexBackPressure(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId) throws IOException {

        return client.jobVertice().jobVertexBackPressure(jobId, vertexId);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-flamegraph
     */
    @GetMapping("{jobId}/vertices/{vertexId}/flamegraph")
    @ApiOperation("Returns flame graph information for a vertex, and may initiate flame graph sampling if necessary.")
    public CompletableFuture<JobVertexFlameGraph> jobVertexFlameGraph(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId,
            @RequestParam("type") String type) throws IOException {

        return client.jobVertice().jobVertexFlameGraph(jobId, vertexId, type);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-metrics
     */
    @GetMapping("{jobId}/vertices/{vertexId}/metrics")
    @ApiOperation("Provides access to task metrics.")
    public CompletableFuture<MetricCollectionResponseBody> jobVertexMetrics(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId,
            @RequestParam("get") String get) throws IOException {

        return client.jobVertice().jobVertexMetrics(jobId, vertexId, get);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-subtasks-accumulators
     */
    @GetMapping("{jobId}/vertices/{vertexId}/subtasks/accumulators")
    @ApiOperation("Returns all user-defined accumulators for all subtasks of a task.")
    public CompletableFuture<SubtasksAllAccumulatorsInfo> jobVertexSubtaskAccumulators(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId) throws IOException {

        return client.jobVertice().jobVertexSubtaskAccumulators(jobId, vertexId);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-subtasks-accumulators
     */
    @GetMapping("{jobId}/vertices/{vertexId}/subtasks/metrics")
    @ApiOperation("Provides access to aggregated subtask metrics.")
    public CompletableFuture<MetricCollectionResponseBody> jobVertexSubtaskMetrics(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId,
            @RequestParam("get") String get,
            @RequestParam("agg") String agg,
            @RequestParam("subtasks") String subtasks) throws IOException {

        return client.jobVertice().jobVertexSubtaskMetrics(jobId, vertexId, get, agg, subtasks);
    }

    /**
     * https://nightlies.apache.org/flink/flink-docs-release-1.13/docs/ops/rest_api/#jobs-jobid-vertices-vertexid-subtasks-subtaskindex
     */
    @GetMapping("{jobId}/vertices/{vertexId}/subtasks/{subtaskIndex}")
    @ApiOperation("Returns details of the current or latest execution attempt of a subtask.")
    public CompletableFuture<SubtaskExecutionAttemptDetailsInfo> jobVertexSubtaskDetail(
            @PathVariable("jobId") String jobId,
            @PathVariable("vertexId") String vertexId,
            @RequestParam("subtaskIndex") Integer subtaskIndex) throws IOException {

        return client.jobVertice().jobVertexSubtaskDetail(jobId, vertexId, subtaskIndex);
    }








}
