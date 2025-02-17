
/*
package knu.discord.notice;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class RedisTestContainerConfig {

    */
/*private static final String REDIS_IMAGE = "redis:7.0.8-alpine";
    private static final int REDIS_PORT = 6379;
    private static final GenericContainer REDIS_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer(REDIS_IMAGE)
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);
        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("redis.host", REDIS_CONTAINER::getHost);
        registry.add("redis.port", () -> REDIS_CONTAINER.getMappedPort(REDIS_PORT)
                .toString());
    }*//*


    private static final String REDIS_IMAGE = "redis:latest";
    private static final int REDIS_PORT = 6379;
    private static final String MYSQL_IMAGE = "mysql:8";

    private static final GenericContainer REDIS;
    private static final JdbcDatabaseContainer MYSQL;

    static {
        REDIS = new GenericContainer(DockerImageName.parse(REDIS_IMAGE))
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);
        MYSQL = new MySQLContainer(MYSQL_IMAGE);

        REDIS.start();
        MYSQL.start();
    }

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry){
        registry.add("redis.host", REDIS::getHost);
        registry.add("redis.port", () -> String.valueOf(REDIS.getMappedPort(REDIS_PORT)));
    }
}
*/
