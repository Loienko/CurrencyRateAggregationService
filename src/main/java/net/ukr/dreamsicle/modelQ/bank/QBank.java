package net.ukr.dreamsicle.modelQ.bank;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import net.ukr.dreamsicle.model.bank.Bank;


/**
 * QBank is a Querydsl query type for Bank
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBank extends EntityPathBase<Bank> {

    private static final long serialVersionUID = 253985345L;

    public static final QBank bank = new QBank("bank");

    public final ListPath<net.ukr.dreamsicle.model.atm.ATM, net.ukr.dreamsicle.model.atm.QATM> atms = this.<net.ukr.dreamsicle.model.atm.ATM, net.ukr.dreamsicle.model.atm.QATM>createList("atms", net.ukr.dreamsicle.model.atm.ATM.class, net.ukr.dreamsicle.model.atm.QATM.class, PathInits.DIRECT2);

    public final StringPath bankCode = createString("bankCode");

    public final StringPath bankName = createString("bankName");

    public final StringPath city = createString("city");

    public final StringPath iban = createString("iban");

    public final StringPath id = createString("id");

    public final ListPath<net.ukr.dreamsicle.model.office.Office, net.ukr.dreamsicle.model.office.QOffice> offices = this.<net.ukr.dreamsicle.model.office.Office, net.ukr.dreamsicle.model.office.QOffice>createList("offices", net.ukr.dreamsicle.model.office.Office.class, net.ukr.dreamsicle.model.office.QOffice.class, PathInits.DIRECT2);

    public final ListPath<net.ukr.dreamsicle.model.partners.Partner, net.ukr.dreamsicle.model.partners.QPartner> partners = this.<net.ukr.dreamsicle.model.partners.Partner, net.ukr.dreamsicle.model.partners.QPartner>createList("partners", net.ukr.dreamsicle.model.partners.Partner.class, net.ukr.dreamsicle.model.partners.QPartner.class, PathInits.DIRECT2);

    public final ListPath<net.ukr.dreamsicle.model.product.Product, net.ukr.dreamsicle.model.product.QProduct> products = this.<net.ukr.dreamsicle.model.product.Product, net.ukr.dreamsicle.model.product.QProduct>createList("products", net.ukr.dreamsicle.model.product.Product.class, net.ukr.dreamsicle.model.product.QProduct.class, PathInits.DIRECT2);

    public final StringPath state = createString("state");

    public final StringPath street = createString("street");

    public QBank(String variable) {
        super(Bank.class, forVariable(variable));
    }

    public QBank(Path<? extends Bank> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBank(PathMetadata metadata) {
        super(Bank.class, metadata);
    }

}

