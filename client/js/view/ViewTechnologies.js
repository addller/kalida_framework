class ViewTechnologies extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.div('view').a(
            A.jView(new JTable(LANG.name, LANG.type, LANG.description, '',''), this, 'viewTableTechnologies')
        )
    }

    addRowTechnology(technology){
        let row = E.tr();
        row.a(
            E.td().t(technology.name),
            E.td().t(technology.typeTechnology.toLowerCase().replace('_', ' ')),
            E.td().t(technology.description),
            E.td().a(E.span().c('delete')),
            E.td().a(E.span().c('edit'))
        )

        this.$viewTableTechnologies.addBodyRow(row)
        return row;
    }

}