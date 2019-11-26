package net.ukr.dreamsicle.modelQ.work;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import net.ukr.dreamsicle.model.work.WorkTimes;


/**
 * QWorkTimes is a Querydsl query type for WorkTimes
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QWorkTimes extends BeanPath<WorkTimes> {

    private static final long serialVersionUID = -1792881563L;

    public static final QWorkTimes workTimes = new QWorkTimes("workTimes");

    public final EnumPath<java.time.DayOfWeek> dayOfWeek = createEnum("dayOfWeek", java.time.DayOfWeek.class);

    public final StringPath endWork = createString("endWork");

    public final StringPath startWork = createString("startWork");

    public QWorkTimes(String variable) {
        super(WorkTimes.class, forVariable(variable));
    }

    public QWorkTimes(Path<? extends WorkTimes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWorkTimes(PathMetadata metadata) {
        super(WorkTimes.class, metadata);
    }

}

