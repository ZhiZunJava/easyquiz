package com.can.easyquiz;

import com.can.easyquiz.config.property.SystemConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Log4j2
@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(value = {SystemConfig.class})
public class EasyquizApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(EasyquizApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path =  env.getProperty("server.servlet.context-path");

        if(null ==path){
            path = "";
        }

        log.info("\n----------------------------------------------------------\n\t轻松考考试系统启动成功，访问路径如下:\n\t本地路径: \t\thttp://localhost:{}{}/\n\t网络地址: \thttp://{}:{}{}/\n----------------------------------------------------------", port, path, ip, port, path);
    }

}
