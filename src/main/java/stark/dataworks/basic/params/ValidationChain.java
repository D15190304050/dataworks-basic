package stark.dataworks.basic.params;

import java.util.LinkedList;
import java.util.function.Function;

public class ValidationChain<TParam>
{
    private final TParam argToValidate;
    private final LinkedList<Function<TParam, String>> validators;

    public ValidationChain(TParam argToValidate)
    {
        this.argToValidate = argToValidate;
        validators = new LinkedList<>();
    }

    /**
     *
     * @param validator A function that validate the argument. If the argument is valid, then the function returns null,
     *                  otherwise, the function returns error message.
     */
    public void addValidator(Function<TParam, String> validator)
    {
        ArgumentValidator.requireNonNull(validator, "validator");
        validators.add(validator);
    }

    public String validate()
    {
        for (Function<TParam, String> validator : validators)
        {
            String errorMessage = validator.apply(argToValidate);
            if (errorMessage != null)
                return errorMessage;
        }

        return null;
    }
}
