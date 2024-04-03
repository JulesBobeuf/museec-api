package fr.univartois.butinfo.s5a01.musicmatcher.database;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;

import fr.univartois.butinfo.s5a01.musicmatcher.document.DatabaseSequence;
import fr.univartois.butinfo.s5a01.musicmatcher.service.SequenceGeneratorService;

@SpringBootTest
class DatabaseSequenceGeneratorTest {

	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@SpyBean
	private MongoOperations mongoOperations;
	
	@MockBean
	private DatabaseSequence counter1;
	
	@Test
	void testSequenceGenerator() {
		when(mongoOperations.findAndModify(query(where("_id").is("aSequence")),
			      new Update().inc("seq",1), options().returnNew(true).upsert(true),
			      DatabaseSequence.class)).thenReturn(counter1);
		
		int i = sequenceGeneratorService.generateSequence("aSequence");
		
		assertThat(sequenceGeneratorService.generateSequence("aSequence")).isEqualTo(i+1);
	}
}
