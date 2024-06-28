package com.example.finance.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitialTaskListener implements ApplicationListener<ApplicationReadyEvent> {

    @EventListener(classes = ApplicationReadyEvent.class)
    public void initializeTask() {
        log.info("Application is ready");
    }

    @EventListener(classes = ApplicationPreparedEvent.class)
    public void initializeTaskPreparedEvent() {
        System.out.println("Application prepared");
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application is ready v2");
    }
}
