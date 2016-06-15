package com.centerm.base;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
/**
 * Log4j级别过滤
 *
 */
public class AppenderLeveLFilter extends DailyRollingFileAppender{
	
	public boolean isAsSevereAsThreshold(Priority priority) {    
          //只判断是否相等，而不判断优先级
        return this.getThreshold().equals(priority);    
    }  
}
