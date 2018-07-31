package org.robertux.financeAnalytics.FinanceAnalyticsServer.data.repositories;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.Sequence;
import org.jooq.TableField;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;
import org.robertux.financeAnalytics.FinanceAnalyticsServer.data.DataSourceConfigurator;

public abstract class BaseRepository<T extends UpdatableRecord<T>> {
	protected Logger logger = LogManager.getLogger(this.getClass());
	protected DataSourceConfigurator config = new DataSourceConfigurator();
	
	protected DSLContext getContext(Connection cn) {
		return DSL.using(cn, SQLDialect.POSTGRES_9_5);
	}
	
	public int add(T rec) {
		try (Connection cn = config.getConnection()) {
			DSLContext ctx = getContext(cn);

			rec.attach(ctx.configuration());
			return rec.store();

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción agregando el registro {0}", rec), ex);
			return 0;
		}
	}
	
	public int add(T rec, TableField<T, Long> keyField, Sequence<Long> seq) {
		try (Connection cn = config.getConnection()) {
			DSLContext ctx = getContext(cn);

			rec.set(keyField, ctx.nextval(seq));
			rec.attach(ctx.configuration());
			return rec.store();

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción agregando el registro {0}", rec), ex);
			return 0;
		}
	}
	
	public int update(T rec) {
		try (Connection cn = config.getConnection()) {
			DSLContext ctx = getContext(cn);

			rec.attach(ctx.configuration());
			return rec.store();

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción actualizando el registro {0}", rec), ex);
			return 0;
		}
	}
	
	public int delete(T rec) {
		try (Connection cn = config.getConnection()) {
			DSLContext ctx = getContext(cn);

			rec.attach(ctx.configuration());
			return rec.delete();

		} catch (Exception ex) {
			this.logger.error(new MessageFormatMessage("Excepción eliminando el registro {0}", rec), ex);
			return 0;
		}
	}
}
