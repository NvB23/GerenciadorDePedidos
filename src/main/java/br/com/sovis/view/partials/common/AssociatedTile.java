package br.com.sovis.view.partials.common;

import br.com.sovis.model.User;
import br.com.sovis.view.style.Variables;
import totalcross.ui.Check;
import totalcross.ui.Label;
import totalcross.ui.ListContainer;

public class AssociatedTile extends ListContainer.Item {
    private final User user;
    private Check userCheck;
    private boolean isChecked = false;

    public AssociatedTile(ListContainer.Layout layout, User user) {
        super(layout);
        this.items = new String[]{""};
        this.user = user;
        setRect(0, 0, FILL, PREFERRED + 10);
    }

    public AssociatedTile(ListContainer.Layout layout, User user, boolean isChecked) {
        super(layout);
        this.items = new String[]{""};
        this.user = user;
        this.isChecked = isChecked;
        setRect(0, 0, FILL, PREFERRED + 10);
    }

    @Override
    public void initUI() {
        userCheck = new Check();
        userCheck.setForeColor(Variables.PRIMARY_COLOR);
        userCheck.setChecked(isChecked);
        add(userCheck, LEFT + 10, CENTER);
        Label emailUserLabel = new Label(user.getEmail());
        emailUserLabel.setForeColor(Variables.PRIMARY_COLOR);
        add(emailUserLabel, AFTER + 20, CENTER);
    }

    public Long getUserAssociated() {
        if (userCheck.isChecked()) {
            return user.getId();
        }
        return null;
    }
}
