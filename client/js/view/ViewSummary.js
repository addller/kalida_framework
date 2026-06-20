class ViewSummary extends JView{
    constructor(){
        super().mount_()
    }

    _init(){
        A.div('view').a(
            A.p("txtSummary").t(LANG.summary),
            A.jView(new ViewSubject(LANG.clients), this, 'viewSubjectClients'),
            A.jView(new ViewSubject(LANG.technologies), this, 'viewSubjectTechnologies'),
            A.jView(new ViewSubject(LANG.experiences), this, 'viewSubjectExperiences')
        )
    }

}
