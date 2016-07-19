package andrey.timeit.model;

/**
 * Created by Andrey on 18.07.2016.
 */
public class ModelAdvice {
    String text;
    double coeff;

    public ModelAdvice() {

    }

    public ModelAdvice(String message, double key) {
        this.text = message;
        this.coeff = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }
}
