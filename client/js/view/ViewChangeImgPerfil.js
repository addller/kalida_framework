class ViewChangeImgPerfil extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.form('formChangeImgPerfil').a(
            A.inputFile('inputUpdateImgPerfil'),
            A.inputSubmit('btnChangeImgPerfil').v(LANG.update),
            A.img('imgPreview')
        )
    }

}