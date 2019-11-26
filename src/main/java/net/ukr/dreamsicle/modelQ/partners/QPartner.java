package net.ukr.dreamsicle.modelQ.partners;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import net.ukr.dreamsicle.model.partners.Partner;


/**
 * QPartner is a Querydsl query type for Partner
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPartner extends EntityPathBase<Partner> {

    private static final long serialVersionUID = 95510418L;

    public static final QPartner partner = new QPartner("partner");

    public final StringPath atmList = createString("atmList");

    public final StringPath bankList = createString("bankList");

    public final StringPath id = createString("id");

    public QPartner(String variable) {
        super(Partner.class, forVariable(variable));
    }

    public QPartner(Path<? extends Partner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPartner(PathMetadata metadata) {
        super(Partner.class, metadata);
    }

}

