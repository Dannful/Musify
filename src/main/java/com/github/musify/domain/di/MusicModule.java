package com.github.musify.domain.di;

import com.github.musify.data.repository.MusicProcesser;
import com.github.musify.domain.repository.AlternativeMusicProcesser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusicModule {

    @Bean
    public MusicProcesser musicProcesser() {
        return new AlternativeMusicProcesser();
    }
}
