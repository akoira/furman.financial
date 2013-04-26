package by.dak.common.lang;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 27.12.2008
 * Time: 18:44:51
 */
public interface ToStringConverter<E> extends Converter<E, String>
{
    @Override
    String convert(E entity);

}
