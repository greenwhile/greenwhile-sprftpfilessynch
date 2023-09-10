package ua.uhmc.sprftpfilessynch.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {

    private static final Logger logger = LogManager.getLogger(RabbitMqConfig.class);

    @Bean
    public Queue grib2queue() {
        return new Queue(Constants.GRIB2_QUEUE);
    }

    @Bean
    public Queue binaryqueue() {
        return new Queue(Constants.BINARY_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constants.METEO_DATA_EXCHANGE);
    }

    @Bean
    public Binding grib2binding(Queue grib2queue, TopicExchange exchange) {
        return BindingBuilder.bind(grib2queue).to(exchange).with(Constants.GRIB2_ROUTING_KEY);
    }

    @Bean
    public Binding binarybinding(Queue binaryqueue, TopicExchange exchange) {
        return BindingBuilder.bind(binaryqueue).to(exchange).with(Constants.BINARY_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}