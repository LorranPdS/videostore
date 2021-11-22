package br.ce.wcaquino.matchers;

import java.util.Calendar;

// 3) veja que agora criamos um matcher próprio. Criamos esta classe e aqui colocamos de forma mais descritiva

public class MatchersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
}
