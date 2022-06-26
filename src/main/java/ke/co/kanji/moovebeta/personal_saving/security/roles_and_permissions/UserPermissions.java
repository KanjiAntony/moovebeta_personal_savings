package ke.co.kanji.moovebeta.personal_saving.security.roles_and_permissions;

public enum UserPermissions {

    SAVING_CRUD("savings:crud"),
    BLOCK_USERS("user:block");

    public String permissions;

    UserPermissions(String perm) {
        this.permissions = perm;
    }

    public String getPermissions() {
        return permissions;
    }

}
