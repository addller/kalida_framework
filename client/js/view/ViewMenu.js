class ViewMenu extends JView{
    constructor() {
        super().mount_()
        this._addTooltips();
    }

    _init() {
        A.div('view').a(
            A.span('spanLogo'),
            A.span('iconSearch').c('sprite'),   
            A.inputSearch('inputSearch').p(LANG.search),
            A.inputSelect("inputLang").a(
                A.inputOption('optPortuguesBr').otv("Português - BR", 'pt-BR'),
                A.inputOption('optEnglishUS').otv("English - US", 'en-US')
            ),
            A.jView(new ViewMenuItemUser(), this)
        )
    }

    _addTooltips(){
        this.$spanLogo.title = LANG.home
        this.$inputLang.value = LANG.cod_lang
    }

}