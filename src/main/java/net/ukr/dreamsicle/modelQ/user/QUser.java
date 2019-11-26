package net.ukr.dreamsicle.modelQ.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import net.ukr.dreamsicle.model.user.QRole;
import net.ukr.dreamsicle.model.user.Role;
import net.ukr.dreamsicle.model.user.StatusType;
import net.ukr.dreamsicle.model.user.User;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1913674847L;

    public static final QUser user = new QUser("user");

    public final DateTimePath<java.sql.Timestamp> created = createDateTime("created", java.sql.Timestamp.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final SetPath<Role, net.ukr.dreamsicle.model.user.QRole> roles = this.<Role, net.ukr.dreamsicle.model.user.QRole>createSet("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final EnumPath<StatusType> status = createEnum("status", StatusType.class);

    public final DateTimePath<java.sql.Timestamp> updated = createDateTime("updated", java.sql.Timestamp.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

