//package com.forezp.adminserver;
//
//
//import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
//import de.codecentric.boot.admin.server.notify.CompositeNotifier;
//import de.codecentric.boot.admin.server.notify.Notifier;
//import de.codecentric.boot.admin.server.notify.RemindingNotifier;
//import de.codecentric.boot.admin.server.notify.filter.FilteringNotifier;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import java.time.Duration;
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class NotifierConfig {
//
//    private final InstanceRepository repository;
//    private final ObjectProvider<List<Notifier>> otherNotifiers;
//
//    public NotifierConfig(InstanceRepository repository, ObjectProvider<List<Notifier>> otherNotifiers) {
//        this.repository = repository;
//        this.otherNotifiers = otherNotifiers;
//    }
//
//    @Bean
//    public FilteringNotifier filteringNotifier() {
//        CompositeNotifier delegate = new CompositeNotifier(otherNotifiers.getIfAvailable(Collections::emptyList));
//        return new FilteringNotifier(delegate, repository);
//    }
//
//    @Primary
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public RemindingNotifier remindingNotifier() {
//        RemindingNotifier notifier = new RemindingNotifier(filteringNotifier(), repository);
//        notifier.setReminderPeriod(Duration.ofMinutes(10));
//        notifier.setCheckReminderInverval(Duration.ofSeconds(10));
//        return notifier;
//    }
//}
