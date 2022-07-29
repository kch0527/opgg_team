package opgg.opgg.web.controller.summoner;

import lombok.RequiredArgsConstructor;
import opgg.opgg.service.summoner.SummonerService;
import opgg.opgg.web.dto.league.LeagueEntryDTO;
import opgg.opgg.web.dto.summoner.SummonerDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchSummoner {

    private final SummonerService searchSummonerService;

    @GetMapping
    public String searchSummoner(Model model, @RequestParam String name) {

        SummonerDTO summonerDTO = searchSummonerService.SearchSummoner(name);
        String id = summonerDTO.getId();
        LeagueEntryDTO leagueEntryDTO = searchSummonerService.searchLeagueEntry(id);

        model.addAttribute("summoner", summonerDTO);
        model.addAttribute("leagueEntry", leagueEntryDTO);

        model.addAttribute("imgURL",
                "http://ddragon.leagueoflegends.com/cdn/9.16.1/img/profileicon/"+summonerDTO.getProfileIconId()+".png");

        return "summoner/result";
    }
}
