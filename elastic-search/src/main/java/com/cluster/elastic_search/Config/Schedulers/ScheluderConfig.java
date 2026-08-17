package com.cluster.elastic_search.Config.Schedulers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;



/*
    Clase de configuracion para usar un pool de hilos para agilizar el proceso de ejecucion
    mediante un pool de hilos de ejecucion.

*/

@Configuration
public class ScheluderConfig {
    @Bean   
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(6);
        scheduler.setThreadNamePrefix("scheduled-task-");
        return scheduler;
    }
}
