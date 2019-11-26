package net.ukr.dreamsicle.modelQ.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import net.ukr.dreamsicle.model.user.Role;
import net.ukr.dreamsicle.model.user.RoleType;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = -1913767860L;

    public static final QRole role = new QRole("role");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<RoleType> name = createEnum("name", RoleType.class);

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

