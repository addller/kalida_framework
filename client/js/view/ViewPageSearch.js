class ViewPageSearch extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.div('view').a(
            A.div('groupSearchAndClose').a(
                A.span('spanSearchResults').t(LANG.search_results.toUpperCase()),
                A.span('spanClose').t('X')
            ),
            A.div('divSearchs')
        )
    }

    addViewRowSearch(viewRowSearch){
        this.$divSearchs.a(viewRowSearch)
    }

    clearSearchs(){
        removeChildren(this.$divSearchs)
    }

}