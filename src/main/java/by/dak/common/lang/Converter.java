package by.dak.common.lang;

/**
 * @author Denis Koyro
 * @version 0.1 05.02.2009
 * @introduced [Furniture constructor | Iteration 1]
 * @since 1.0.0
 */
public interface Converter<S, D>
{
    String EMPTY_STRING = "";
    String NULL_STRING = "< >";

    D convert(S source);
}
