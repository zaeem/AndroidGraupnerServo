package com.graupnerservo;

import android.app.Application;

public class AndroidApplication extends Application {
	private static AndroidApplication sInstance;
	public String selectedStellzeit,selectedStellmoment,size2,selectedGewicht,selectedHVValue,selectedBrushlessValue,selectedLagerung,selectedGetriebe,selectedText;
	public String rightText,impressum;
    public static AndroidApplication getInstance() {
      return sInstance;
    }
    @Override
    public void onCreate() {
      super.onCreate();
      this.rightText = "Mit dieser App findest du zuverlässig die passenden Servos zu deinem Flugmodell. Du kannst dabei direkt die GRAUPNER-Artikelnummer eingeben oder nach unterschiedlichen Kriterien suchen.";
      this.selectedStellzeit = "";
      this.selectedStellmoment = "";
      this.size2 = "";
      this.selectedGewicht = "";
      this.selectedHVValue = "";
      this.selectedBrushlessValue="";
      this.selectedLagerung = "";
      this.selectedGetriebe = "";
      this.selectedText = "";
      sInstance = this;
      this.impressum="<b>Name und Anschrift</b><br>" +
      		"Graupner GmbH & Co. KG<br>" +
      		"Sitz der Gesellschaft: Kirchheim/Teck<br>" +
      		"Amtsgericht Stuttgart HRA 230523<br>" +
      		"<br>" +
      		"Persönlich haftende Gesellschafterin:<br>" +
      		"Graupner Verwaltungs-GmbH<br>" +
      		"Sitz der Gesellschaft: Kirchheim/Teck<br>" +
      		"Amtsgericht Stuttgart HRB 231188<br>" +
      		"<br>" +
      		"Geschäftsführer: Stefan Graupner<br>" +
      		"<br>" +
      		"Telefon/Fax/E-Mail-Adresse<br>" +
      		"Telefon: 07021 722-0<br>" +
      		"Fax: 07021 722-200<br>" +
      		"Email: info@graupner.de<br>" +
      		"<br>" +
      		"Umsatzsteueridentifikationsnummer<br>" +
      		"DE 145929079<br>" +
      		"<br><br><br>" +
      		"<b>Entwickelt von</b><br>" +
      		"exaro Vermarktung<br>" +
      		"Eichenstrasse 51<br>" +
      		"74229 Oedheim<br>" +
      		"<br>" +
      		"Telefon: 07136 2870-208<br>" +
      		"Fax: 07136 2870-209<br>" +
      		"Email: community@rc-modellscout.de<br>" +
      		"Internet: http://www.rc-modellscout.de<br>" +
      		"<br><br><br>" +
      		"<b>Inhalte dieser App</b><br>" +
      		"Die Erstellung dieser App erfolgte mit größtmöglicher Sorgfalt. Für den Inhalt und die Richtigkeit in dieser App vermittelten Informationen kann daher nicht gehaftet werden. Die Haftung für Schäden, die durch die indirekte oder direkte Verwendung dieser App entstehen wird daher ausgeschlossen, sofern nicht gesetzliche Regelungen dem Ausschluss entgegenstehen. Die App kann Links auf Webseiten oder Dienstleistungen Dritter enthalten. Für die Inhalte dieser Verlinkungen wird keine Verantwortung übernommen noch wird der Inhalt der Webseiten zu eigen gemacht, da die verlinkten Informationen weder kontrolliert noch Verantwortung für die dort bereit gehaltenen Inhalte und Informationen übernommen werden kann. Die Nutzung erfolgt einzig und allein auf eigenes Risiko des Nutzers.<br>" +
      		"<br><br>" +
      		"<b>Datenschutz</b><br>" +
      		"Vielen Dank, dass Sie diese App geladen haben. Danke auch für das Interesse an unseren Unternehmen und den Produkten. Über diese App erfassen wir keinerlei personenbezogene Daten, außer wenn Sie solche Daten freiwillig zur Verfügung stellen (z.B. durch Registrierung) bzw. eingewilligt haben oder die entsprechenden Rechtsvorschriften über den Schutz Ihrer Daten dies erlauben. Die von Ihnen zur Verfügung gestellten Daten werden nur verwendet um Anfragen zu beantworten, Aufträge zu bearbeiten oder Zugang zu bestimmten Informationen oder Angeboten zu verschaffen. Für Anregungen, Fragen und Beschwerden hinsichtlich der Bearbeitung persönlicher Daten, wenden Sie sich bitte an die entsprechend angegebenen Kontaktadressen. <br>" +
      		"<br><br>" +
      		"<b>Copyright</b><br>" +
      		"Alle Rechte vorbehalten. Text, Bilder, Sound, Animationen, Videos und Grafiken in dieser App unterliegen dem Urheberrecht. Dies gilt auch für deren Anordnung. Ohne die vorherige schriftliche Erlaubnis von Graupner oder exaro Vermarktung ist es nicht gestattet, die Inhalte dieser App zu veröffentlichen oder zu verbreiten, zu ändern, zu übertragen, wiederzuverwenden, öffentlich wiederzugeben oder zu öffentlichen oder für kommerzielle Zwecke zu verwenden. <br>";
    		  }
}
