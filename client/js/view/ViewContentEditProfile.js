class ViewContentEditProfile extends JView{
    constructor(user){
        super({user}).mount_()
    }

    _init(){
        A.div().a(
            A.jView(new ViewChangeImgPerfil(), this),   
            A.jView(new ViewEditProfile(this.user), this),
            A.jView(new ViewChangePassword(this.user), this)
        )
    }

}