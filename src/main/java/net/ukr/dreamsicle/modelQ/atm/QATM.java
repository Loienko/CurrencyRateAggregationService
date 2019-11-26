package net.ukr.dreamsicle.modelQ.atm;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import net.ukr.dreamsicle.model.atm.ATM;


/**
 * QATM is a Querydsl query type for ATM
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QATM extends EntityPathBase<ATM> {

    private static final long serialVersionUID = -283437735L;

    public static final QATM aTM = new QATM("aTM");

    public final StringPath bankCode = createString("bankCode");

    public final StringPath city = createString("city");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath state = createString("state");

    public final StringPath street = createString("street");

    public final SetPath<net.ukr.dreamsicle.model.work.WorkTimes, net.ukr.dreamsicle.model.work.QWorkTimes> workTimes = this.<net.ukr.dreamsicle.model.work.WorkTimes, net.ukr.dreamsicle.model.work.QWorkTimes>createSet("workTimes", net.ukr.dreamsicle.model.work.WorkTimes.class, net.ukr.dreamsicle.model.work.QWorkTimes.class, PathInits.DIRECT2);

    public QATM(String variable) {
        super(ATM.class, forVariable(variable));
    }

    public QATM(Path<? extends ATM> path) {
        super(path.getType(), path.getMetadata());
    }

    public QATM(PathMetadata metadata) {
        super(ATM.class, metadata);
    }

}

