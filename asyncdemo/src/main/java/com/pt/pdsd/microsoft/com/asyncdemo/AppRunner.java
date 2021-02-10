package com.pt.pdsd.microsoft.com.asyncdemo;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final GitHubLookupService gitHubLookupService;

    public AppRunner(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
        CompletableFuture<User> page2 = gitHubLookupService.findUser("Microsoft");
        CompletableFuture<User> page3 = gitHubLookupService.findUser("petetian");

        CompletableFuture.allOf(page1, page2, page3).join();

        logger.info("Elapsed time: {}",
                (System.currentTimeMillis() - start));
        logger.info("--> {}", page1.get());
        logger.info("--> {}", page2.get());
        logger.info("--> {}", page3.get());
    }
}
