class ViewContentExperience extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.div().a(
            A.jView(new ViewIncludeExperience(), this),
            A.p('txtMyExperiences').t(LANG.my_experiences.toUpperCase()),
            A.div('divExperiences').a(
                A.p('txtHaveNotExperiences').t(LANG.have_not_experiences)
            )
        )
    }

    addViewRowExperience(viewRowExperience){
        this.$divExperiences.a(viewRowExperience)
    }

}