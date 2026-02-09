package br.com.sovis.view.partials.client;

import br.com.sovis.model.User;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Check;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;

public class AssociatedClientTile extends ListContainer.Item {
    private final User user;
    private Check userChek;

    public AssociatedClientTile(ListContainer.Layout layout, User user) {
        super(layout);
        this.items = new String[]{""};
        this.user = user;
        setRect(0, 0, FILL, PREFERRED + 10);
    }

    @Override
    public void initUI() {
        userChek = new Check();
        userChek.setForeColor(Variables.PRIMARY_COLOR);
        add(userChek, LEFT + 10, CENTER);
        Label emailUserLabel = new Label(user.getEmail());
        emailUserLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(emailUserLabel, AFTER + 20, CENTER);
    }

    public Long getUserAssociated() {
        if (userChek.isChecked()) {
            return user.getId();
        }
        return null;
    }
}
