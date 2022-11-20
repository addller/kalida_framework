class ViewClients extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.div('view').a(
            A.div('divControls').a(
                A.label('lblChooseAll').a(
                    A.inputCheckBox('inputChooseAll').ck()
                ).t(LANG.choose_all, true)
            ),
            A.jView(new JTable(LANG.name, LANG.user_name, LANG.nickName,'','',''), this, 'viewTableUsers')
        )
    }

    addRowClient(user, applyRowfunction){
        let tableRow = E.tr();
        tableRow.a(
            E.td().t(user.name),
            E.td().t(user.username),
            E.td().t(user.nickName),
            E.td().a(E.span().c('delete')),
            E.td().a(E.span().c('edit')),
            E.td().a(E.span().c('details')),
        )
        this.$viewTableUsers.addBodyRow(tableRow, applyRowfunction)

        this.$viewTableUsers.zebrar()
    }

}