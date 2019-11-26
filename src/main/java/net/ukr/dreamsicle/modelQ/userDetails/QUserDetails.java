package net.ukr.dreamsicle.modelQ.userDetails;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import net.ukr.dreamsicle.model.userDetails.UserDetails;


/**
 * QUserDetails is a Querydsl query type for UserDetails
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserDetails extends EntityPathBase<UserDetails> {

    private static final long serialVersionUID = 1298110099L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserDetails userDetails = new QUserDetails("userDetails");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath phone = createString("phone");

    public final StringPath surname = createString("surname");

    public final net.ukr.dreamsicle.model.user.QUser user;

    public QUserDetails(String variable) {
        this(UserDetails.class, forVariable(variable), INITS);
    }

    public QUserDetails(Path<? extends UserDetails> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserDetails(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserDetails(PathMetadata metadata, PathInits inits) {
        this(UserDetails.class, metadata, inits);
    }

    public QUserDetails(Class<? extends UserDetails> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new net.ukr.dreamsicle.model.user.QUser(forProperty("user")) : null;
    }

}

