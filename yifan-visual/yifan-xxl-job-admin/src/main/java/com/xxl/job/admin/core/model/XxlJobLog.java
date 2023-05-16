package com.xxl.job.admin.core.model;

import java.util.Date;

import lombok.Data;

/**
 * XxlJobLog
 *
 * @author Wenzhou
 * @since 2023/5/10 20:37
 */
@Data
public class XxlJobLog {
    private long id;

    /**
     * job info
     */
    private int jobGroup;

    private int jobId;

    /**
     * execute info
     */
    private String executorAddress;

    private String executorHandler;

    private String executorParam;

    private String executorShardingParam;

    private int executorFailRetryCount;

    /**
     * trigger info
     */
    private Date triggerTime;

    private int triggerCode;

    private String triggerMsg;

    /**
     * handle info
     */
    private Date handleTime;

    private int handleCode;

    private String handleMsg;

    /**
     * alarm info
     */
    private int alarmStatus;
}
