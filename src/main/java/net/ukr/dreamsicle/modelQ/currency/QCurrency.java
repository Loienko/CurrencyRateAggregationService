package net.ukr.dreamsicle.modelQ.currency;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import net.ukr.dreamsicle.model.currency.Currency;


/**
 * QCurrency is a Querydsl query type for Currency
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCurrency extends EntityPathBase<Currency> {

    private static final long serialVersionUID = 837223905L;

    public static final QCurrency currency = new QCurrency("currency");

    public final StringPath bankName = createString("bankName");

    public final StringPath currencyCode = createString("currencyCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath purchaseCurrency = createString("purchaseCurrency");

    public final StringPath saleOfCurrency = createString("saleOfCurrency");

    public QCurrency(String variable) {
        super(Currency.class, forVariable(variable));
    }

    public QCurrency(Path<? extends Currency> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCurrency(PathMetadata metadata) {
        super(Currency.class, metadata);
    }

}

