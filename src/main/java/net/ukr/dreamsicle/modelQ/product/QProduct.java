package net.ukr.dreamsicle.modelQ.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.model.product.TypeProduct;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -368406525L;

    public static final QProduct product = new QProduct("product");

    public final StringPath bankCode = createString("bankCode");

    public final StringPath description = createString("description");

    public final StringPath id = createString("id");

    public final EnumPath<TypeProduct> type = createEnum("type", TypeProduct.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

