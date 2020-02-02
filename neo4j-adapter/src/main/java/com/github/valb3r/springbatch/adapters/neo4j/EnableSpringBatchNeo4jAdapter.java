package com.github.valb3r.springbatch.adapters.neo4j;

import com.github.valb3r.springbatch.adapters.neo4j.config.Neo4jBatchConfigurer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(Neo4jBatchConfigurer.class)
public @interface EnableSpringBatchNeo4jAdapter {
}
