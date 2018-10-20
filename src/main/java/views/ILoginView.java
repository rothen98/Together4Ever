package views;

public interface ILoginView {
    void addTextLimiterToTextFields(int i);

    void showSignUpError(String username);

    void showLoginError();

    void setController(ILoginController controller);

    void reset();
}
