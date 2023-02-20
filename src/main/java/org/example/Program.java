package org.example;

import jdk.jfr.Timespan;

import javax.sound.sampled.AudioFormat;
import java.io.Console;
import java.util.Dictionary;
import java.util.Objects;
import java.util.function.Consumer;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

public class Program {
    public static void main(String[] args) { //C# CODDING
        Console.WriteLine("starting consumer");
        var config = new Dictionary<String, object>(){// CONFIGURATION
            {"group.id","dotnet-consumer-group"},
            {"bootstrap.servers","kafka-1:9092"},
            {"auto.commit.interval.ms",5000},
            {"auto.offset.reset","earliest"}

        };

        var deserializer= new StringDeserializer(Encoding.UTF8);
        using (var consumer= new Consumer<String,String >(config,deserializer,deserializer)){

            consumer.OnMessage+=(_, msg)=>
        Console.WriteLine($"Read('{msg.Key}','{msg.Value}') from:{msg.TopicPArtitionOffset}");// message handling

        consumer.OnError+=(_,error)=>
        Console.WriteLine($"Error:{error}");

        consumer.OnConsumeError+=(_,msg)=>
        Console.WriteLine($"Consume error ({msg.TopicPartitionOffset}): {msg.Error} ");//error handling

        consumer.Subscribe("starting topic"); //subscribing to topic

        while(true){
            consumer.Poll(TimeSpan.FromMilliseconds(100)); //polling data
        }




        }

    }
}
