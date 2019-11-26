package net.ukr.dreamsicle.modelQ.office;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import net.ukr.dreamsicle.model.office.Office;


/**
 * QOffice is a Querydsl query type for Office
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOffice extends EntityPathBase<Office> {

    private static final long serialVersionUID = -878238655L;

    public static final QOffice office = new QOffice("office");

    public final StringPath bankCode = createString("bankCode");

    public final StringPath city = createString("city");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath state = createString("state");

    public final StringPath street = createString("street");

    public final SetPath<net.ukr.dreamsicle.model.work.WorkTimes, net.ukr.dreamsicle.model.work.QWorkTimes> workTimes = this.<net.ukr.dreamsicle.model.work.WorkTimes, net.ukr.dreamsicle.model.work.QWorkTimes>createSet("workTimes", net.ukr.dreamsicle.model.work.WorkTimes.class, net.ukr.dreamsicle.model.work.QWorkTimes.class, PathInits.DIRECT2);

    public QOffice(String variable) {
        super(Office.class, forVariable(variable));
    }

    public QOffice(Path<? extends Office> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOffice(PathMetadata metadata) {
        super(Office.class, metadata);
    }

}

