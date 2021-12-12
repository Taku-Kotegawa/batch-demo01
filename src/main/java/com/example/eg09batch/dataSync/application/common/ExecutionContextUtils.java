package com.example.eg09batch.dataSync.application.common;


import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;

public class ExecutionContextUtils {

    /**
     *
     * @param stepContribution
     * @param key
     * @return
     */
    public static Object getContext(StepContribution stepContribution, String key) {
        Object o = null;
        var iterator = stepContribution.getStepExecution().getJobExecution().getStepExecutions().iterator();
        while (iterator.hasNext()) {
            StepExecution s = iterator.next();
            if (s.getExecutionContext().containsKey(key)) {
                o = s.getExecutionContext().get(key);
            }
        }
        return o;
    }

}
