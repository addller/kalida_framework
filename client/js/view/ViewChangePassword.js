class ViewChangePassword extends JView{
    constructor(user){
        super(user).mount_()
    }

    _init(){
        A.form('formChangePassword').a(
            A.p('txtChangePassword').t(LANG.changePassword.toUpperCase()),
            A.label('lblCurrentPassword').t(LANG.current_password),
            A.inputPassword('inputUpdateCurrentPassword'),
            A.label('lblNewPassword').t(LANG.new_password),
            A.inputPassword('inputUpdateNewPassword'),
            A.inputSubmit('btnUpdatePassword').v(LANG.update)
        )
    }

    _limits(){
        this.$inputUpdateNewPassword.maxLength = 200
        this.$inputUpdateCurrentPassword.maxLength = 200
    }

}