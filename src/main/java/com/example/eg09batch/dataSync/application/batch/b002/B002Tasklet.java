package com.example.eg09batch.dataSync.application.batch.b002;

import com.example.eg09batch.dataSync.application.repository.MUserRepository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.eg09batch.dataSync.application.batch.b002.B002Constants.TASKLET_NAME;


@Component(TASKLET_NAME)
@StepScope
public class B002Tasklet implements Tasklet {

    @Autowired
    SqlSessionFactory sqlSessionFactory;


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);

        MUserRepository mapper = sqlSession.getMapper(MUserRepository.class);




        System.out.println("B002Tasklet running");

        return RepeatStatus.FINISHED;
    }
}
