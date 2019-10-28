package net.ukr.dreamsicle.exception;

import net.ukr.dreamsicle.util.Constants;

/**
 * ResourceIsStaleException class that extends {@link RuntimeException}
 *
 * @author yurii.loienko
 * @version 1.0
 * @deprecated
 */
@Deprecated
public class ResourceIsStaleException extends RuntimeException {

    public ResourceIsStaleException() {
        super(Constants.DATA_IS_STALE_PLEASE_RETRY);
    }
}