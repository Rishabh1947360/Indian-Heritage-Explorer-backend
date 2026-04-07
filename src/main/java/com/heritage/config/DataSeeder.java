package com.heritage.config;

import com.heritage.entity.Artisan;
import com.heritage.entity.HeritageSite;
import com.heritage.repository.ArtisanRepository;
import com.heritage.repository.HeritageSiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.heritage.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.heritage.entity.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final HeritageSiteRepository siteRepository;
    private final ArtisanRepository artisanRepository;
    

    @Override
    public void run(String... args) {
    	seedUsers();
        seedHeritageSites();
        seedArtisans();
    }
    
    private void seedHeritageSites() {
        if (siteRepository.count() > 0) return;

        List<HeritageSite> sites = List.of(
            site("up-1","Taj Mahal","Uttar Pradesh","Agra",
                "https://images.unsplash.com/photo-1698824834792-b50f1747730a?w=1080",
                "The Taj Mahal is an ivory-white marble mausoleum on the right bank of the river Yamuna in Agra. Commissioned in 1632 by Mughal emperor Shah Jahan to house the tomb of his favourite wife, Mumtaz Mahal.",
                "1632-1653","₹250","₹1,300","6:00 AM - 7:00 PM","Fridays","October - March","7,000 - 8,000",true,4.9,12543),
            site("up-2","Agra Fort","Uttar Pradesh","Agra",
                "https://images.unsplash.com/photo-1587474260584-136574528ed5?w=1080",
                "Agra Fort is a historical fort in the city of Agra. It was the main residence of the emperors of the Mughal Dynasty until 1638.",
                "1565-1573","₹50","₹650","6:00 AM - 6:00 PM","None","October - March","3,000 - 4,000",true,4.7,8234),
            site("up-3","Fatehpur Sikri","Uttar Pradesh","Fatehpur Sikri",
                "https://images.unsplash.com/photo-1599661046289-e31897846e41?w=1080",
                "Fatehpur Sikri was founded in 1571 by Mughal Emperor Akbar and served as the capital of the Mughal Empire from 1571 to 1585.",
                "1571-1573","₹50","₹610","6:00 AM - 6:00 PM","None","October - March","2,000 - 3,000",true,4.6,5432),
            site("ka-1","Hampi","Karnataka","Hampi",
                "https://images.unsplash.com/photo-1616671832048-a8becc25bb13?w=1080",
                "Hampi is an ancient village dotted with numerous ruined temple complexes from the Vijayanagara Empire.",
                "14th-16th century","₹40","₹600","6:00 AM - 6:00 PM","None","October - February","1,500 - 2,000",true,4.8,9876),
            site("ka-2","Mysore Palace","Karnataka","Mysore",
                "https://images.unsplash.com/photo-1600697230109-b3fe5b1bfb11?w=1080",
                "Mysore Palace is a historical palace and the official residence of the Wadiyar dynasty and the seat of the Kingdom of Mysore.",
                "1897-1912","₹100","₹200","10:00 AM - 5:30 PM","None","October - March","10,000+",false,4.7,15234),
            site("dl-1","Red Fort","Delhi","Old Delhi",
                "https://images.unsplash.com/photo-1713590781837-788d6e2c448f?w=1080",
                "The Red Fort is a historic fort that served as the main residence of the Mughal emperors for nearly 200 years.",
                "1638-1648","₹35","₹500","9:30 AM - 4:30 PM","Mondays","October - March","5,000 - 6,000",true,4.5,11234),
            site("dl-2","Qutub Minar","Delhi","Mehrauli",
                "https://images.unsplash.com/photo-1739356025254-ef2fd922e0a0?w=1080",
                "Qutub Minar is a minaret and victory tower forming part of the Qutb complex. It is the tallest brick minaret in the world.",
                "1193","₹35","₹550","7:00 AM - 5:00 PM","None","October - March","4,000 - 5,000",true,4.6,9543),
            site("rj-1","Amber Fort","Rajasthan","Jaipur",
                "https://images.unsplash.com/photo-1599661046289-e31897846e41?w=1080",
                "Amber Fort is known for its artistic style, blending Hindu and Mughal elements. It is a UNESCO World Heritage Site.",
                "1592","₹100","₹500","8:00 AM - 5:30 PM","None","October - March","5,000+",true,4.7,10234),
            site("rj-2","Mehrangarh Fort","Rajasthan","Jodhpur",
                "https://images.unsplash.com/photo-1524492412937-b28074a5d7da?w=1080",
                "Mehrangarh Fort is one of the largest forts in India, situated 400 feet above Jodhpur with several palaces known for their intricate carvings.",
                "1459","₹100","₹600","9:00 AM - 5:00 PM","None","October - March","3,000+",false,4.8,8765),
            site("mh-1","Ajanta Caves","Maharashtra","Aurangabad",
                "https://images.unsplash.com/photo-1548013146-72479768bada?w=1080",
                "The Ajanta Caves are approximately 30 rock-cut Buddhist cave monuments dating from the 2nd century BCE to about 480 CE.",
                "2nd century BCE","₹40","₹600","9:00 AM - 5:30 PM","Mondays","November - March","2,000+",true,4.7,7654),
            site("mh-2","Ellora Caves","Maharashtra","Aurangabad",
                "https://images.unsplash.com/photo-1566837497312-7be7830ae9b5?w=1080",
                "Ellora is a UNESCO World Heritage Site with 34 caves representing Buddhist, Hindu and Jain rock-cut temples.",
                "6th-11th century","₹40","₹600","6:00 AM - 6:00 PM","Tuesdays","November - March","3,000+",true,4.8,8543),
            site("tn-1","Brihadeeswarar Temple","Tamil Nadu","Thanjavur",
                "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?w=1080",
                "The Brihadeeswarar Temple is a Hindu temple dedicated to Shiva, and one of the largest South Indian temples. It is a UNESCO World Heritage Site.",
                "1003-1010 CE","Free","Free","6:00 AM - 12:00 PM, 4:00 PM - 8:30 PM","None","November - February","5,000+",true,4.8,11234),
            site("gj-1","Rani ki Vav","Gujarat","Patan",
                "https://images.unsplash.com/photo-1590136571374-8e9e4cb8b18e?w=1080",
                "Rani ki Vav is an intricately constructed stepwell on the banks of Saraswati River, built as a memorial in the 11th century.",
                "11th century","₹40","₹600","8:00 AM - 6:00 PM","None","October - March","1,000+",true,4.7,6543),
            site("kl-1","Hill Palace","Kerala","Tripunithura",
                "https://images.unsplash.com/photo-1590136571374-8e9e4cb8b18e?w=1080",
                "Hill Palace is the largest archaeological museum in Kerala and was the official residence of the Cochin Royal Family.",
                "1865","₹20","₹200","9:00 AM - 12:30 PM, 2:00 PM - 4:30 PM","Mondays","November - February","500+",false,4.4,3456)
        );

        siteRepository.saveAll(sites);
        System.out.println("✅ Seeded " + sites.size() + " heritage sites");
    }

    private void seedArtisans() {
        if (artisanRepository.count() > 0) return;

        List<Artisan> artisans = List.of(
            Artisan.builder().name("Ramesh Kumar").craft("Traditional Pottery").state("Rajasthan")
                .location("Jaipur").experience("25 years").phone("+91 98765 43210")
                .email("ramesh.pottery@example.com")
                .image("https://images.unsplash.com/photo-1760764541302-e3955fbc6b2b?w=400")
                .specialty("Blue pottery - Traditional Rajasthani technique").build(),
            Artisan.builder().name("Lakshmi Devi").craft("Handloom Weaving").state("Tamil Nadu")
                .location("Kanchipuram").experience("30 years").phone("+91 98765 43211")
                .email("lakshmi.weaving@example.com")
                .image("https://images.unsplash.com/photo-1611574557351-00889b20a36b?w=400")
                .specialty("Kanchipuram silk sarees with traditional motifs").build(),
            Artisan.builder().name("Suresh Patel").craft("Metal Craftsmanship").state("Gujarat")
                .location("Vadodara").experience("20 years").phone("+91 98765 43212")
                .email("suresh.metalcraft@example.com")
                .image("https://images.unsplash.com/photo-1650726583448-dda0065f2f11?w=400")
                .specialty("Bronze sculptures and temple artifacts").build(),
            Artisan.builder().name("Meera Sharma").craft("Miniature Painting").state("Rajasthan")
                .location("Udaipur").experience("15 years").phone("+91 98765 43213")
                .email("meera.painting@example.com")
                .image("https://images.unsplash.com/photo-1650726583448-dda0065f2f11?w=400")
                .specialty("Mughal-style miniature paintings on ivory paper").build()
        );

        artisanRepository.saveAll(artisans);
        System.out.println("✅ Seeded " + artisans.size() + " artisans");
    }
    private void seedUsers() {
        if (userRepository.count() > 0) return;

        User user = User.builder()
                .name("Admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("1234"))
                .build();

        userRepository.save(user);

        System.out.println("✅ Seeded user: admin@example.com / 1234");
    }

    private HeritageSite site(String id, String name, String state, String location,
                               String image, String description, String yearBuilt,
                               String feeIndian, String feeForeign, String hours,
                               String closedOn, String bestTime, String dailyVisitors,
                               boolean unesco, double rating, int reviews) {
        return HeritageSite.builder()
                .id(id).name(name).state(state).location(location)
                .image(image).description(description).yearBuilt(yearBuilt)
                .entryFeeIndian(feeIndian).entryFeeForeign(feeForeign)
                .openingHours(hours).closedOn(closedOn).bestTime(bestTime)
                .dailyVisitors(dailyVisitors).unesco(unesco)
                .rating(rating).reviews(reviews)
                .virtualTourUrl("").virtualTourVideoUrl("")
                .build();
    }
}
