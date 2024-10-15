//          █████╗ ██████╗ ██████╗ ███████╗██╗     ██████╗  █████╗ ██╗  ██╗███╗   ███╗ █████╗ ███╗   ██╗    ███████╗ █████╗ ██╗   ██╗███████╗██████╗
//         ██╔══██╗██╔══██╗██╔══██╗██╔════╝██║     ██╔══██╗██╔══██╗██║  ██║████╗ ████║██╔══██╗████╗  ██║    ██╔════╝██╔══██╗╚██╗ ██╔╝██╔════╝██╔══██╗
//         ███████║██████╔╝██║  ██║█████╗  ██║     ██████╔╝███████║███████║██╔████╔██║███████║██╔██╗ ██║    ███████╗███████║ ╚████╔╝ █████╗  ██║  ██║
//         ██╔══██║██╔══██╗██║  ██║██╔══╝  ██║     ██╔══██╗██╔══██║██╔══██║██║╚██╔╝██║██╔══██║██║╚██╗██║    ╚════██║██╔══██║  ╚██╔╝  ██╔══╝  ██║  ██║
//         ██║  ██║██████╔╝██████╔╝███████╗███████╗██║  ██║██║  ██║██║  ██║██║ ╚═╝ ██║██║  ██║██║ ╚████║    ███████║██║  ██║   ██║   ███████╗██████╔╝
//         ╚═╝  ╚═╝╚═════╝ ╚═════╝ ╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝    ╚══════╝╚═╝  ╚═╝   ╚═╝   ╚══════╝╚═════╝

package com.backend.tomagram;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.core.KafkaTemplate;


@SpringBootApplication
//@EnableFeignClients
public class TomagramApplication {

    public static void main(String[] args) {
        SpringApplication.run(TomagramApplication.class, args);
    }

}
